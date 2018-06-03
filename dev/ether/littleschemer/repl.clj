(ns ether.littleschemer.repl
  (:require
   [cider.nrepl :as cider]
   [clojure.java.io :as io]
   [clojure.tools.nrepl.server :as nrepl.server]
   [refactor-nrepl.middleware :as refactor]))

(defn clean-up! []
  (-> (io/file ".nrepl-port")
      (io/delete-file true)))

(defn -main [& [repl-port web-port cljs-build]]
  (let [repl-port  (or (some-> repl-port Integer/parseInt) 6661)]
    (prn "Starting repl" {:port repl-port})
    (nrepl.server/start-server
     :port    repl-port
     :handler (refactor/wrap-refactor cider/cider-nrepl-handler))
    (spit ".nrepl-port" repl-port)
    (prn "Done repl.")))
