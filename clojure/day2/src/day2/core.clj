(ns day2.core (:gen-class :main true))

(defn -main [& args]
  (defn parse [row]
     (def numbers (-> row
         (get 0)
         (clojure.string/split #"-")
     ))
     (list (read-string (get numbers 0)) (read-string (get numbers 1)) (subs (get row 1) 0 1) (get row 2))
  )

  (defn countMatches [row]
     (list (nth row 0) (nth row 1) (count (re-seq (re-pattern (nth row 2)) (nth row 3))))
  )

  (defn isValid [row]
     (and (<= (nth row 0) (nth row 2)) (>= (nth row 1) (nth row 2)))
  )

  (with-open [rdr (clojure.java.io/reader "src/day2/passwords.txt")]
    (->>
         (line-seq rdr)
         (map #(clojure.string/split % #" "))
         (map parse)
         (map countMatches)
         (filter isValid)
         (count)
         (println)
    )
  )
)
