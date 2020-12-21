(ns day21.core)

(defn parse-menu [menu] (->>
  (-> 
    menu
    (clojure.string/replace #",|\(|\)" "")
    (clojure.string/split-lines)
  )
  (map #(clojure.string/split % #" "))
  (map (fn [dish] (map #(into [] %) (partition-by #(= "contains" %) dish))))
  (map #(remove #{'("contains")} %))
  (mapv #(zipmap [:ingredients :allergens] %))
))

(defn -main
  [& args]
  (println "Hello World"))
