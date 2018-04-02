(set-env!
  :source-paths #{"src" "html"}
  :resource-paths #{"html"}
  :dependencies '[ [adzerk/boot-cljs "2.1.4" :scope "test"]
                   [adzerk/boot-reload "0.5.2" :scope "test"]
                   [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                   [org.martinklepsch/boot-garden "1.3.2-1"]
                   [pandeiro/boot-http "0.8.3" :scope "test"]
                   [org.clojure/clojurescript "1.10.217" :scope "test"]
                   [org.clojure/core.async "0.4.474"]
                   [prismatic/dommy "1.1.0"]
                   [hiccups "0.3.0"]
                   [funcool/beicon "4.1.0"]
                   [com.cerner/clara-rules "0.17.0"]
                   [com.cemerick/piggieback "0.2.2" :scope "test"]
                   [weasel "0.7.0" :scope "test"]
                   [org.clojure/clojure "1.10.0-alpha4"]
                   [org.clojure/data.csv "0.1.4"]
                   [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                   [datascript "0.16.4"]])


(require
  '[adzerk.boot-cljs :refer [cljs]]
  '[adzerk.boot-reload :refer [reload]]
  '[pandeiro.boot-http :refer [serve]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])

(task-options!
  cljs { :optimizations :none
         :source-map true}
  reload { :on-jsload 'es-edu.app/init})

(deftask dev []
  (comp
    (serve) (watch) (reload) (cljs-repl) (cljs) (target)))


     

                   
