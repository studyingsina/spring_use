;排列组合
;http://guozi149.me/
;ps (1 2 3) 
;  ps '(2 3) ps-rest
;    ps '(3) ps-rest
;      ps () ps-rest
;      append (()) 3 => (() (3))
;  (() (3) (2) (2 3))
;(() (3) (2) (2 3) (1) (1 3) (1 2) (1 2 3))

;(define (ps set)
;  (if (null? set) '(())
;      (let ((ps-rest (ps (cdr set)))) 
;        (append ps-rest
;                (map (lambda (subset)
;                       (cons (car set) subset))
;                     ps-rest)))))

(define (ps set)
  (
    if (null? set) '(())
    (
      let ((ps-rest (ps (cdr set))))
      (
        append ps-rest 
        (map (lambda (subset) (cons (car set) subset))
             ps-rest)
      )
    )
  )
)
