(ns day3.core (:gen-class :main true))

(defn -main [& args]
  (def slope (->>
    (->
      (slurp "resources/slope.txt")
      (clojure.string/split #"\n")
    )
    (map #(clojure.string/split % #""))
  ))
  (defn getSlopeAt [x, y]
    (nth (nth slope x) y)
  )

  (defn race [down, right]
    (def width (count (first slope)))
    (def height (count slope))
    (->>
      (range 0 height down)
      (map-indexed vector)
      (map #(list (second %) (rem (* (first %) right) width)))
      (map #(getSlopeAt (first %) (second %)))
      (filter #(= % "#"))
      (count)
    )
  )

  (->>
    (race 1 1)
    (* (race 1 3))
    (* (race 1 5))
    (* (race 1 7))
    (* (race 2 1))
    (println)
  )
)
