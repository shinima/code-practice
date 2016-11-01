(require '[clojure.string :refer [join]])
(import (solutions Problem_1079))

(defn rand
  ([start end]
   (+ start (rand-int (- end start))))
  ([end]
   (rand 1 end)))

(def N 100)
(def L 1000)

(dotimes [_ 1000]
  (def posters (for [_ (range N)]
                 (let [[start end] (sort [(rand-int L) (rand-int L)])]
                   [start (inc end)])))
  (Problem_1079/test
    (join " "
          (apply concat [N L] posters))))
