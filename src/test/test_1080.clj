(ns test
  (:require [clojure.string :refer [join]])
  (:import (solutions Problem_1080)))

(defn random
  ([start end]
   (+ start (rand-int (- end start))))
  ([end]
   (random 1 end)))

(def N 300000) ; 价格数量
(def M 300000) ; 操作数量
(def price-range 100)
(def repeat-times 1)

(def R \newline)

(dotimes [_ repeat-times]
  (def prices (repeatedly N #(random (- price-range) price-range)))
  (def operations
    (repeatedly M (fn []
                    (let [[start end] (sort [(rand-int N) (rand-int N)])]
                      [(rand-int 1) start end (random (- price-range) price-range)]))))
  (def input (join " "
                   (apply concat [(dec N) M R] prices [R]
                          (interleave operations (repeat [R])))))
  (Problem_1080/naiveTest input)
  (Problem_1080/excitingTest input)

  (println "exciting:")
  (time (Problem_1080/excitingTest input))
  (println "naive:")
  (time (Problem_1080/naiveTest input))
  )
