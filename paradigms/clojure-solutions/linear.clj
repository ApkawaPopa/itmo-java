(defn binmap [f] (fn [x y] (mapv f x y)))

(def v+ (binmap +'))
(def v- (binmap -'))
(def v* (binmap *'))
(def vd (binmap /))

(def m+ (binmap v+))
(def m- (binmap v-))
(def m* (binmap v*))
(def md (binmap vd))

(defn scalar [u v] (reduce +' (v* u v)))

(defn unfmap [f] (fn [x y] (mapv (fn [a] (f a y)) x)))
(def v*s (unfmap *'))
(def m*s (unfmap v*s))
(def m*v (unfmap scalar))

(defn transpose [m] (apply mapv vector m))
(defn m*m [A B] (mapv (fn [v] (m*v (transpose B) v)) A))

(defn vect [u v]
  (letfn [(det22 [i j] (- (*' (u i) (v j)) (*' (u j) (v i))))]
    [(det22 1 2) (det22 2 0) (det22 0 1)]))

(defn recmap [f] (fn r [x y] (if (vector? x) (mapv r x y) (f x y))))
(def s+ (recmap +'))
(def s- (recmap -'))
(def s* (recmap *'))
(def sd (recmap /))
