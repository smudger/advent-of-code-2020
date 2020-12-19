(ns day19.core)

(defn expandRule [rules [id _]]
  (->>
    rules
    (vals)
    (map #(clojure.string/replace % (re-pattern (str "(?<= |^|\\()" id "(?= |$|\\))")) (str "(" (get rules id) ")")))
    (zipmap (keys rules))
  )
)

(defn -main [& args] (let [[rawRules messages] (->>
  (->
    (slurp "resources/input.txt")
    (clojure.string/split #"\n\n")
  )
  (map #(clojure.string/split % #"\n"))
)]
  (let [ruleMap (->>
    rawRules
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
