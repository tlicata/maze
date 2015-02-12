(defproject maze "0.1.0-SNAPSHOT"
  :description "Maze generation in ClojureScript"
  :url ""
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2843"]]
  :plugins [[lein-cljsbuild "1.0.4"]]
  :source-paths ["src" "target/classes"]
  :clean-targets ["out" "out-adv" "target"]
  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]
              :compiler {
                :main maze.core
                :output-to "out/maze.js"
                :output-dir "out"
                :optimizations :none
                :cache-analysis true
                :source-map true}}
             {:id "prod"
              :source-paths ["src"]
              :compiler {
                :main maze.core
                :output-to "out-adv/maze.js"
                :output-dir "out-adv"
                :optimizations :advanced
                :pretty-print false}}]})
