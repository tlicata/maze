(ns maze.core)

(enable-console-print!)

(println "Hello, World!")

(defn grid [w h]
  (set (concat
        ;; horizontal walls
        (for [x (range (dec w)) y (range h)]
          #{[x y] [(inc x) y]})
        ;; vertical walls
        (for [x (range w) y (range (dec h))]
          #{[x y] [x (inc y)]}))))
