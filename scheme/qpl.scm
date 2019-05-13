;排列组合
;http://guozi149.me/
;items (1 2 3) 
;  items (2 3) 
;  items (1 3)
;  items (1 2)

(define (permute items)
  (if (null? items) '(())
      (apply append
             (map (lambda (ele)
                    (map (lambda (pers) (cons ele pers)) (permute (delete ele items))))
                  items))))
