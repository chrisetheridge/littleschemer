(ns ether.littleschemer.color)

(def ^:const CHARS "0123456789ABCDEF")

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
  (to-rgba [this]))

(defrecord RGBAColor [red green blue alpha]
  IColor
  (toString [this]
    (str "RGBA: " red " " green " " blue " " alpha))
  (to-hex [this]
    (str "#" (hex-value red) (hex-value green) (hex-value blue)))
  (to-rgb [this]
    (RGBColor. red green blue))
  (to-hsl [this])
  (to-hsv [this]))

(defrecord RGBColor [red green blue]
  IColor
  (toString [this]
    (str "RGBA: " red " " green " " blue))

  (to-hex [this]
    (str "#" (hex-value red) (hex-value green) (hex-value blue)))

  (to-hsv [this]
    (let [r      (num red)
          g      (num green)
          b      (num blue)
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
  )

(defrecord HCLColor [])

(defrecord HSVColor [hue saturation value]
  IColor
  (toString [this]
    (str "HSV: " hue " " saturation " " value)))

(defrecord LABColor [lightness green-red blue-yellow]
  IColor
  (toString [this]
    (str "LAB: " lightness " " green-red "  blue-yellow")))

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
