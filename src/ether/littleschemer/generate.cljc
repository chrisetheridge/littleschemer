(ns ether.littleschemer.generate
  (:require [ether.littleschemer.color :as color]
            [ether.littleschemer.util :as util]))

(defn generate-rgb-color [mix]
  (->> (take 3 (repeatedly #(rand-int 256)))
       (map (comp #(/ % 2) +) (color/_values mix))
       (map util/to-number)
       (apply color/->RGBColor)))

(comment

  (let [mix  (color/->RGBColor 255 255 255)
        colors (take 12 (repeatedly #(generate-rgb-color mix)))]
    colors)

  )
