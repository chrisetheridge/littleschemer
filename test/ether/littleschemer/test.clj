(ns ether.littleschemer.test
  (:require [clojure.test :refer [deftest testing is]]
            [ether.littleschemer.color :as color]))

(deftest color-parsing
  (let [[r g b :as real-green] [125 250 125]
        real-green-hex         "#7DFA7D"
        green-color-a          (color/->RGBAColor r g b 0)
        green-color            (color/->RGBColor r g b)]
    (testing "RGBA"
      (is (= real-green-hex (color/to-hex green-color-a))))
    (testing "RGB"
      (is (= real-green-hex (color/to-hex green-color))))))

(deftest color-conversion
  (let [[r g b :as real-green] [125 250 125]
        rgb-green              (color/->RGBColor r g b)
        hsv                    (color/to-hsv rgb-green)
        [h s v]                [120.0 0.5000 0.98039216]]
    (testing "RGB->HSV"
      (is (= h (float (:hue hsv))))
      (is (= s (float (:saturation hsv))))
      (is (= v (float (:value hsv)))))))
