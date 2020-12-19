(ns day19.core)

(defn expandRule [rules [id _]]
  (->>
    rules
    (vals)
    (map #(clojure.string/replace % (re-pattern (str "(?<= |^|\\()" id "(?= |$|\\+|\\))")) (str "(" (get rules id) ")")))
    (zipmap (keys rules))
  )
)

(defn repeatRule11 [n] (->>
  (range 1 n)
  (map #(str " (42){" % "} (31){" % "} |"))
  (reduce #(str %1 %2) "11:")
  (drop-last 2) 
  (reduce str)
))

(defn -main [& args] (let [[rawRules messages] (->>
  (->
    (slurp "resources/input.txt")
    (clojure.string/split #"\n\n")
  )
  (map #(clojure.string/split % #"\n"))
)]
  (def maxLength (->>
    messages
    (map count)
    (sort)
    (last)
  ))

  (let [ruleMap (->>
    rawRules
    (map #(clojure.string/replace % #"11: 42 31" (repeatRule11 maxLength)))
    (map #(clojure.string/replace % #"\"" ""))
    (map #(clojure.string/split % #": "))
    (into {})
  )] 
    (def rules (->>
      ruleMap
      (reduce expandRule ruleMap)
      (map #(list (first %) (clojure.string/replace (second %) #" " "")))
      (map #(list (first %) (re-pattern (second %))))
      (map #(into [] %))
      (into {})
    ))

    (->>
      messages
      (map #(re-matches (get rules "0") %))
      (remove nil?)
      (count)
      (println)
    )
  )
))
