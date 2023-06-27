(ns origamibot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [opencv4.core :refer :all :as origami]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t])
  (:gen-class))

(def token (env :telegram-token))

(defn apply-cv [filename]
  (-> filename
      (origami/imread)
      (origami/cvt-color! origami/COLOR_RGB2GRAY)
      (origami/canny! 300.0 100.0 3 true)
      (origami/bitwise-not!)
      (origami/imwrite filename)))

(h/defhandler handler

  (h/message-fn
    (fn [{{id :id} :chat :as message}]
      (let [fid (-> message :photo last :file_id) filename (str fid ".png")]
        (t/download-file token fid)
        (apply-cv filename)
        (t/send-photo token id (clojure.java.io/as-file filename))))))


(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provide token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the origamibot")
  (<!! (p/start token handler)))
