(ns day18.core)

(def ops {"+" + "*" *})

(defn parseChar [char]
  (if (re-find #"\d" char)
      (read-string char)
      (get ops char)
  ))

(defn evaluateSimple [expr] (let [[start & rest] (->>
  (clojure.string/split expr #" ")
  (map parseChar)
  )] (->>
       (partition 2 rest)
       (reduce (fn [sum next] ((first next) sum (last next))) start)
       (str)
    )
))

(defn evaluateAdditionFirst [expr] (->>
  (clojure.string/split expr #" \* ")
  (map (comp read-string evaluateSimple))
  (reduce *)
  (str)
))

(defn evaluateBrackets [expr] (->>
  (re-seq #"\(([^\(\)]*)\)" expr)
  (map #(list (first %) (evaluateAdditionFirst (last %))))
  (reduce (fn [expr [from to]] (clojure.string/replace expr from to)) expr)
  ))

(defn evaluateExpression [expr]
  (if (re-find #"\(([^\(\)]*)\)" expr)
      (recur (evaluateBrackets expr))
      ((comp read-string evaluateAdditionFirst) expr)
  )
)

(defn -main [& args] (->>
  (->
    (slurp "resources/expressions.txt")
    (clojure.string/split #"\n")
  )
  (map evaluateExpression)
  (reduce +)
  (println)
))
