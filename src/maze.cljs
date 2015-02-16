(ns maze.core
  (:require [om.core :as om]
            [om.dom :as dom]))

(enable-console-print!)

;; From "Clojure Programming", page 145.
;;
;;   Wilson's algorithm is a carving algorithm; it takes a fully walled
;;   "maze" and carves an actual maze out of it by removing some
;;   walls. Its principle is:
;;
;;   1. Randomly pick a location and mark it as _visited_.
;;   2. Randomly pick a location that isn't _visited_ yet - if there is
;;      none, return the maze.
;;   3. Perform a random walk starting from the newly picked location
;;      until you stumble on a location that is _visited_ - if you pass
;;      through a location more than once during the random walk, always
;;      remember the direction you take to leave it.
;;   4. Mark all the locations of the random walk as _visited_, and
;;      remove walls according to the last known "exit direction".
;;   5. Repeat from 2.

(def segment 12)
(def total 40)

;; Creates a fully walled "maze" we will carve a path through.
(defn grid [w h]
  (set (concat
        ;; horizontal walls
        (for [x (range (dec w)) y (range h)]
          #{[x y] [(inc x) y]})
        ;; vertical walls
        (for [x (range w) y (range (dec h))]
          #{[x y] [x (inc y)]}))))

(defn maze
  "Returns a random maze carved out of walls; walls is a set of 2-item
  sets #{a b} where a and b are locations. Locations are 2-item
  vectors [x y] where x and y are coordinates in a grid.
  The returned maze is a set of the remaining walls"
  [walls]
  (let [paths (reduce (fn [index [a b]]
                        (merge-with into index {a [b] b [a]}))
                      {} (map seq walls))
        start-loc (rand-nth (keys paths))]
    (loop [walls walls
           unvisited (disj (set (keys paths)) start-loc)]
      (if-let [loc (when-let [s (seq unvisited)] (rand-nth s))]
        (let [walk (iterate (comp rand-nth paths) loc)
              steps (zipmap (take-while unvisited walk) (next walk))]
          (recur (reduce disj walls (map set steps))
                 (reduce disj unvisited (keys steps))))
        walls))))

(def walls (atom (maze (grid total total))))

(defn render-walls [data owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/svg #js {:width (* segment total) :height (* segment total)}
             (map (fn [wall]
                    (let [[x1 y1] (first wall)
                          [x2 y2] (last wall)]
                      (dom/line #js {:x1 (+ 1 (* x1 segment))
                                     :y1 (+ 1 (* y1 segment))
                                     :x2 (+ 1 (* x2 segment))
                                     :y2 (+ 1 (* y2 segment))
                                     :stroke "black"
                                     :strokeWidth 4})))
                  data)))))

(om/root render-walls walls
         {:target (. js/document (getElementById "container"))})

(js/setInterval (fn []
                  (reset! walls (maze (grid total total))))
                1000)
