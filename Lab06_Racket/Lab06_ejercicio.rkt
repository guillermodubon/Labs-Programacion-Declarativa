(define (interes deposito)
  (cond
    ((<= deposito 40000) (* deposito 0.035))
    ((<= deposito 260000) (* deposito 0.04))
    (else (* deposito 0.045))
  )
)

(interes 20000)
(interes 100000)
(interes 300000)