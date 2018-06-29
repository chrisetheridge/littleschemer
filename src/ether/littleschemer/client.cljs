(ns ether.littleschemer.client
  (:require [ether.littleschemer.generate :as generate]
            [ether.littleschemer.color :as color]
            [rum.core :as rum]))

(rum/defc app []
  [:.container
   [:h1 "Littleschemer sandbox"]
   [:.sandbox
    [:pre (pr-str (generate/generate-rgb-color (color/->RGBColor 255 255 255)))]
    [:br]
    [:.pallete
     (for [c (take 12 (repeatedly #(generate/generate-rgb-color (color/->RGBColor 255 255 255))))]
       [:div {:style {:background-color (apply #(str
                                                 "rgb"
                                                 "(" %1 " " %2 " " %3 ")")
                                               (color/_values c))
                      :width "50px"
                      :height "50px"}}
        " "])]]])

(defn start! []
  (rum/mount (app) (js/document.getElementById "app")))

(start!)
