(ns weatherbot.core
  (:require
    [morse.api :as api]
    [clojure.core.async :refer [<!!]]
    [clojure.string :as str]
    [environ.core :refer [env]]
    [morse.handlers :as h]
    [morse.polling :as p]
    [cheshire.core :refer :all])
  (:gen-class))

; requires 2 environment variables:
; TELEGRAM_TOKEN
; WEATHER_API_TOKEN
(def token (env :telegram-token))
(def weatherApiToken (env :weather-api-token))


(defn weather [city]
  (let [request
        (str "http://api.openweathermap.org/data/2.5/weather?q="
             city
             "&units=metric&APPID="
             weatherApiToken)]
    (:main
      (parse-string (slurp request)
                    (fn [k] (keyword k))))
    ))

(h/defhandler handler
              (h/message-fn
                (fn [{{id :id} :chat :as message}]
                  (let [place (:text message)]
                    (try
                      (api/send-text token id {:parse-mode "Markdown"}
                                     (str "*" place "*" "\n" (weather place)))
                      (catch Exception e))
                    ))))


(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provde token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the weatherbot")
  (<!! (p/start token handler)))
