(defn division [a b] (/ a (double b)))

(defn parser [operationMap varC constC]
  (fn [expression]
    (letfn
      [(parseToken [token]
         (cond
           (list? token) (apply (operationMap (first token)) (mapv parseToken (rest token)))
           (symbol? token) (varC (str token))
           :else (constC token)))]
      (parseToken (read-string expression)))))


;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


(defn constant [value] (fn [args] value))
(defn variable [name] (fn [args] (args name)))

(defn operation [action] (fn [& operands] (fn [args] (apply action (mapv #(% args) operands)))))

(def add (operation +'))
(def subtract (operation -'))
(def multiply (operation *'))
(def divide (operation division))
(def negate subtract)
(def exp (operation #(Math/exp %)))
(def ln (operation #(Math/log (Math/abs %))))

(def functionalOperations {'+ add '- subtract '* multiply '/ divide 'negate negate 'exp exp 'ln ln})

(def parseFunction (parser functionalOperations variable constant))


;///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


(load-file "proto.clj")


(def evaluate (method :evaluate))
(def toString (method :toString))


(def _value (field :value))

(def Constant
  (constructor
    (fn [this value] (assoc this :value value))
    {
     :evaluate (fn [this map] (_value this))
     :toString #(str (_value %))
     }))


(def _name (field :name))

(def Variable
  (constructor
    (fn [this name] (assoc this :name name))
    {
     :evaluate (fn [this map] (map (_name this)))
     :toString #(_name %)
     }))


(def _action (field :action))
(def _operands (field :operands))
(def _sign (field :sign))

(def OperationPrototype
  {
   :evaluate (fn [this map] (apply (_action this) (mapv #(evaluate % map) (_operands this))))
   :toString (fn [this] (str "(" (_sign this) (reduce #(str %1 " " (toString %2)) "" (_operands this)) ")"))
   })

(defn Operation
  [action sign]
  (constructor
    (fn [this & operands] (assoc this :operands operands))
    (assoc OperationPrototype :action action :sign sign)))

(def Add (Operation +' "+"))
(def Subtract (Operation -' "-"))
(def Multiply (Operation *' "*"))
(def Divide (Operation division "/"))
(def Negate (Operation - "negate"))
(def Sin (Operation #(Math/sin %) "sin"))
(def Cos (Operation #(Math/cos %) "cos"))

(def objectOperations
  {
   '+ Add
   '- Subtract
   '* Multiply
   '/ Divide
   'negate Negate
   'sin Sin
   'cos Cos
   })

(def parseObject (parser objectOperations Variable Constant))
