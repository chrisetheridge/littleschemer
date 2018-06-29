(ns ether.littleschemer.client
  (:require [ether.littleschemer.generate :as generate]
            [ether.littleschemer.color :as color]
            [rum.core :as rum]))

(defn generate-pastel-color []
  (generate/generate-rgb-color (color/->RGBColor 255 255 255)))

(defn generate-pastel-pallete [n]
  (take n (repeatedly generate-pastel-color)))

(rum/defcs pallete < (rum/local (generate-pastel-pallete 12) ::*color-pallete)
  [{::keys [*color-pallete]}]
  [:.pallete-container
   [:.generated
    (for [c @*color-pallete]
      [:div {:style {:background-color (apply #(str
                                                "rgb"
                                                "(" %1 " " %2 " " %3 ")")
                                              (color/_values c))
                     :width            "50px"
                     :height           "50px"}}
       " "])]
   [:.generate
    [:button {:on-click #(reset! *color-pallete (generate-pastel-pallete 12))}
     "Generate"]]])

(rum/defc app []
  [:.container
   [:h1 "Littleschemer sandbox"]
   [:.sandbox
    [:pre (pr-str (generate/generate-rgb-color (color/->RGBColor 255 255 255)))]
    [:br]
    [:.pallete
     (pallete)]]])

(defn start! []
  (rum/mount (app) (js/document.getElementById "app")))

(start!)
