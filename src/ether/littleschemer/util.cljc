(ns ether.littleschemer.util)

(defn to-number [n]
  #?(:clj (int n)
     :cljs (js/Number n)))
