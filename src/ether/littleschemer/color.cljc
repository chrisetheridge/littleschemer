(ns ether.littleschemer.color
  (:require [ether.littleschemer.util :as util]))

(def CHARS "0123456789ABCDEF")

(defn hex-value [val]
  (let [parsed (max 0 (min val 255))]
    (str
     (.charAt CHARS (/ (- parsed (mod parsed 16)) 16))
     (.charAt CHARS (mod parsed 16)))))

(defprotocol IColor
  (to-hsl [this])
  (to-rgb [this])
  (to-hsv [this])
  (to-hex [this])
  (to-rgba [this])
  (_values [this]))

(defrecord HSVColor [hue saturation value]
  Object
  (toString [this]
    (str "HSV: " hue " " saturation " " value))
  IColor
  (_values [this]
    [hue saturation value]))

(defrecord LABColor [lightness green-red blue-yellow]
  Object
  (toString [this]
    (str "LAB: " lightness " " green-red "  blue-yellow"))
  IColor
  (_values [this]
    [lightness green-red blue-yellow]))


(defrecord RGBColor [red green blue]
  Object
  (toString [this]
    (str "RGBA: " red " " green " " blue))

  IColor
  (to-hex [this]
    (str "#" (hex-value red) (hex-value green) (hex-value blue)))
  (to-hsv [this]
    (let [r      (util/to-number red)
          g      (util/to-number green)
          b      (util/to-number blue)
          rr     (/ r 255)
          gg     (/ g 255)
          bb     (/ b 255)
          minrgb (min rr (min gg bb))
          maxrgb (max rr (max gg bb))]
      (if (= minrgb maxrgb)
        (HSVColor. 0 0 minrgb)
        (let [d  (cond
                   (= rr minrgb) (- gg bb)
                   (= bb minrgb) (- rr gg)
                   :else         (- bb rr))
              h  (cond
                   (= rr minrgb) 3
                   (= bb minrgb) 1
                   :else         5)
              hh (* 60 (- h (/ d (- maxrgb minrgb))))
              ss (/ (- maxrgb minrgb) maxrgb)]
          (HSVColor. hh ss maxrgb)))))
  ;; TODO
  (to-hsl [this])
  (to-rgba [this])
  (_values [this]
    [red green blue]))

(defrecord RGBAColor [red green blue alpha]
  Object
  (toString [this]
    (str "RGBA: " red " " green " " blue " " alpha))
  IColor
  (to-hex [this]
    (str "#" (hex-value red) (hex-value green) (hex-value blue)))
  (to-rgb [this]
    (RGBColor. red green blue))
  (to-hsl [this])
  (to-hsv [this])
  (_values [this]
    [red green blue alpha]))

(defrecord HCLColor [])

(defn from-hex [hex-str])

(comment

  (let [red     125
        green   250
        blue    125
        opacity 0.5
        color   (RGBColor. (double red)
                           (double green)
                           (double blue))]
    (to-hsv color))

  )
