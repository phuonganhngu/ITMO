section .text

global string_length
global print_newline
global print_string
global read_word
global string_equals


; принимает указатель на строку, возвращает её длину. rcx - счетчик
string_length:
    xor rcx, rcx
    mov rax, rdi
    .loop:
      mov rax, [rdi+rcx]
      inc rcx
      test al, 0xFF
      jnz .loop
      dec rcx
      mov rax, rcx
    ret


; вывод строки, первый аргумент — указатель на неё
; установим rsi, rdx, rdi и rax параметры, перед вызовом sys_write
; rsi — адрес начала, rdx — кол-ство символов
; rdi — поток ВВ, rax — номер системной команды
print_string:
    mov rsi, rdi
    call string_length
    mov rdx, rax
    mov rdi, 1
    mov rax, 1
    syscall
    ret


; аргумент — символ, который нужно вывести в stdout
; rdi - печатаемый символ
print_char:
    push rdi
    mov rax, 1
    mov rsi, rsp
    mov rdi, 1
    mov rdx, 1
    syscall
    pop rdi
    ret


; переход на новую строку
; 0x0A - „\n“
print_newline:
    push rdi
    mov rdi, 0x0A
    call print_char
    pop rdi
    ret


; вывод беззнакового 8-байтового числа в десятичной СС
; методом деления на 10 (0x0A). После div, в rdx падает остаток от деления.
; add rdx, „0“ устанавливает символ rdx до цифрового ASCII-кода
print_uint:
    mov rax, rdi
    mov r10, 0x0A
    push 0x00
    .loop:
    xor rdx, rdx
    div r10
    add rdx, '0'
    push rdx
    cmp rax, r10
    jae .loop
    add rax, '0'
    cmp rax, '0'
    je .next
    push rax
    .next:
    pop rdi
    cmp rdi, 0x00
    je .eof
    call print_char
    ;je .eof
    jmp .next
    .eof:
    ret


; вывод знакового 8-байтного числа в десятичной СС
print_int:
    mov r8, rdi
    test rdi, rdi
    jns .print
    mov rdi, '-'
    call print_char
    mov rdi, r8
    neg rdi
    .print:
      call print_uint
    ret


; принимает два указателя на строки(rax, rdx), сравнивает их посимвольно
; если они равны, то в rax возвращает 1, иначе 0
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


; прочитать с потока ввода один символ
read_char:
    push 0
    mov rdx, 1
    mov rdi, 0
    mov rsi, rsp
    mov rax, 0
    syscall
    pop rax
    ret


section .data
word_buffer times 256 db 0

section .text


; прочитать с потока ввода следующее слово, пропустив перед пробельные символы
; возвращает: в rax - слово, в rdx — длина слова
read_word:
    xor r8, r8
    mov r8, word_buffer
    .skip:
    call read_char
    test rax, rax
    jz .finish
    cmp rax, 32
    jle .skip
    .read_next:
    mov [r8], rax
    inc r8
    call read_char
    cmp rax,  32
    jg .read_next
    .finish:
    mov rdi, word_buffer
    call string_length
    mov rdx, rax
    mov rax, word_buffer
    ret


; прочитать из буфера в памяти беззнаковое число
; вернуть в rax — число, rdx — длину числа
parse_uint:
    xor rcx, rcx
    xor rax, rax
    xor r8, r8
    mov r10, 10
    .num_interval:
    cmp byte[rdi+rcx], '0'
    jb .not_currect
    cmp byte[rdi+rcx], '9'
    ja .not_currect
    mul r10
    mov r8b,  byte[rdi+rcx]
    sub r8b, '0'
    add rax, r8
    inc rcx
    jmp .num_interval
    .not_currect:
    mov rdx, rcx
    ret


; прочитать из буфера в памяти знаковое число
; вернуть в rax — число, rdx — длину числа
parse_int:
    cmp byte[rdi], '-'
    jne parse_uint
    inc rdi
    call parse_uint
    neg rax
    inc rdx
    ret


; принимает указатель на строку и указатель на место в памяти
; после выполнения по второму адресу лежит копия первой строки
string_copy:
    xor rcx, rcx
    mov cl, byte[rdi]
    mov byte[rsi], cl
    inc rdi
    inc rsi
    test rcx, rcx
    jnz string_copy
    ret
