(defproject mytelegrambot "0.1.0-SNAPSHOT"
  :description "My first Telegram bot with Clojure"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [environ             "1.1.0"]
                 [morse               "0.2.4"]]

  :plugins [[lein-environ "1.1.0"]]

  :main ^:skip-aot mytelegrambot.core
  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})
