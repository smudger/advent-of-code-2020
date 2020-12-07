(ns day7.core (:gen-class :main true))

(defn -main [& args]
  (defn parseContents [first & rest] (
    ->> (sequence rest)
        (hash-map first)
  ))

  (defn parseRule [rule] (
    ->> (re-seq #"((?:\w+\s){2})bag" rule)
        (map last)
        (map clojure.string/trimr)
        (filter #(not= "no other" %))
        (apply parseContents)
  ))

  (def bags (->>
    (->
      (slurp "resources/rules.txt")
      (clojure.string/split #"\.\n")
    )
    (map parseRule)
    (into (sorted-map))
  ))

  (defn containsBag [needle haystack] (
    ->> (some #(containsBag needle (get bags %)) haystack)
        (or (some #{needle} haystack))
  ))

  (->>
    (filter #(containsBag "shiny gold" (last %)) bags)
    (count)
    (println)
  )
)
