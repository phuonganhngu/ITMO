section .text

string_length:
        xor rax, rax 
   .loop:
	cmp byte [rdi+rax], 0
	je .end 
	inc rax 
	jmp .loop
   .end:
	ret


print_string:
	mov rsi, rdi 
   	call string_length
    	mov rdx, rax 
   	 mov rax, 1 
    	mov rdi, 1 
    	syscall
   	 ret



print_char:
  push rdi 
    mov rax, 1 
    mov rdi, 1 
    mov rsi, rsp 
    mov rdx, 1 
    syscall
    pop rdi
    ret


print_newline:
    mov rdi, 10
    call print_char


print_uint:
    mov rax, rdi
    mov r9, 10 
    mov r8, rsp 
    dec rsp
    mov byte [rsp], 0 
    .loop:
	xor rdx, rdx 
	div r9 
        	add rdx, 48 
        	dec rsp
        	mov [rsp], dl 
        	test rax, rax 
    jnz .loop
    mov rdi, rsp 
    call print_string
    mov rsp, r8 
    ret



print_int:
    test rdi, rdi 
jns print_uint 
mov rax, rdi 
neg rax
push rax
mov rdi, '-'
call print_char
pop rax
mov rdi, rax
 jmp print_uint


string_equals:
    xor rcx, rcx
    .loop:
   	mov al, byte[rdi+rcx]   
    	mov dl, byte[rsi+rcx]
    	cmp al, dl
    	jne .not_equal
   	inc rcx                        
    	cmp al, 0                    
    	je .equal
    jmp .loop
   .not_equal:
    	mov rax, 0
    ret
    .equal:
   	 mov rax, 1
    ret



read_char:
        push 0
    mov rax, 0 
    mov rdi, 0 
    mov rsi, rsp 
mov rdx, 1 
    syscall
    pop rax
    ret 


section .data
word_buffer times 256 db 0

section .text

read_word:
    mov r8,  -1                   
    mov rdx, 256                
    mov rdi, 0                     
    mov rsi, buffer   
    mov rax, 0                   
    syscall
    .check_loop:                        
    	inc r8
    	cmp byte[buffer + r8], 0x09 
    	jz  .check_loop
    	cmp byte[buffer + r8], 0x0A 
    	jz  .check_loop
    	cmp byte[buffer + r8], 0x20 
    	jz  .check_loop    
    mov r9, r8
    cmp byte[buffer + r8], 0       
    jz  .finish
    inc r8
    .read_loop:
    	cmp byte[buffer + r8], 0x20 
   	jz  .finish
    	cmp byte[buffer + r8], 0
    	jz  .finish
	inc r8
    	jmp .read_loop
    .finish:
    mov byte[buffer + r8], 0 
    lea rax, [buffer + r9]
    sub r8, r9
    mov rdx, r8
    ret


; rdi points to a string
; returns rax: number, rdx : length
parse_uint:
        xor rcx,rcx
    xor rax, rax
    mov r9, 10
    xor r8, r8
    .check_loop:
    	cmp byte[rdi+rcx], '0'       
    	jb .end
    	cmp byte[rdi+rcx], '9'       
    	ja .end
    	mul r9                               
    	mov r8b, byte[rdi+rcx]    
    	sub r8b, '0'                     
    	add rax, r8
    	inc rcx
    	jnz .check_loop
    .end:
    mov rdx, rcx
    ret


; rdi points to a string
; returns rax: number, rdx : length
parse_int:
cmp byte[rdi], '-'          ; Check if the first character is a minus
    jne parse_uint              ; check for negativity and call parse_uint if it is positive
    inc rdi
    push rdi
    call parse_uint
    pop rdi
    neg rax
    inc rdx                      ; negative + '-'
    ret


string_copy:
    push rcx
    .loop:
	xor rcx, rcx 	;reset rcx
	mov cl, byte [rdi] 	; cl-the lower 8 bits of rcx
	mov byte[rsi], cl
	inc rdi
	inc rsi
	test rcx, rcx	;check if rcx is 0
	jnz .loop 	;if rcx is not equal 0 then continue looping
     pop rcx 
    ret

