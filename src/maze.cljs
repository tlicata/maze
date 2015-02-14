(ns maze.core
  (:require [om.core :as om]
            [om.dom :as dom]))

(enable-console-print!)


(def segment 12)
(def total 40)

(defn grid [w h]
  (set (concat
        ;; horizontal walls
        (for [x (range (dec w)) y (range h)]
          #{[x y] [(inc x) y]})
        ;; vertical walls
        (for [x (range w) y (range (dec h))]
          #{[x y] [x (inc y)]}))))

(def walls (grid total total))

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
                  (:walls data))))))

(om/root render-walls {:walls walls}
         {:target (. js/document (getElementById "container"))})
