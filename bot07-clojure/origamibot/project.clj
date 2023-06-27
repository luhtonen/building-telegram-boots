(defproject origamibot "0.1.0-SNAPSHOT"
  :description "OpenCV Telegram bot"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [environ             "1.1.0"]
                 [hellonico/morse     "0.2.4"]
                 [origami          "4.7.0-17"]]

  :plugins [[lein-environ "1.1.0"]]

  :main ^:skip-aot origamibot.core
  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}})
