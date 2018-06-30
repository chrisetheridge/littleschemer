(ns ether.littleschemer.client
  (:require [ether.littleschemer.generate :as generate]
            [ether.littleschemer.color :as color]
            [rum.core :as rum]))

(def dark-grey    (color/->RGBColor 100 100 100))
(def light-yellow (color/->RGBColor 255 245 154))

(defn generate-pallete [mix n]
  (take n (repeatedly #(generate/generate-rgb-color mix))))

(defn render-pallete [pallete]
  (for [cpart (partition-all 4 pallete)]
    [:.cpart {:key (str "color-row/" (hash cpart))}
     (for [c cpart]
       [:div {:style {:background-color (apply #(str
                                                 "rgb"
                                                 "(" %1 " " %2 " " %3 ")")
                                               (color/_values c))
                      :width            "50px"
                      :height           "50px"
                      :display          "inline-block"}}
        " "])
     [:br]]))

(defn generate-color-palletes []
  (map #(generate-pallete % 10)
       [color/white color/black dark-grey]))

(rum/defcs pallete < (rum/local (generate-color-palletes) ::*color-palletes)
  [{::keys [*color-palletes]}]
  [:.pallete-container
   [:.generated
    (for [pallete @*color-palletes]
      [:div
       (render-pallete pallete)
       [:br]])]
   [:.generate
    [:button {:on-click
              (fn [_]
                (reset! *color-palletes (generate-color-palletes)))}
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
