SET SERVEROUTPUT ON FORMAT WRAPPED;

DECLARE
    tableName VARCHAR2(40) := 'Н_ЛЮДИ'; 
    colNo VARCHAR2(128) := 'No.';
    colName VARCHAR2(128) := 'Имя столбца';
    colAttr VARCHAR2(128) := 'Атрибуты';

    noLen NUMBER := 3;
    colLen NUMBER := 15;
    attrLen NUMBER := 60;
    attrNameLen NUMBER := 15;

    dataType VARCHAR2(128);
    comLen NUMBER :=15;

    CURSOR RES IS
        SELECT ALL_TAB_COLUMNS.COLUMN_ID AS COLUMN_ID, 
        ALL_TAB_COLUMNS.COLUMN_NAME AS COLUMN_NAME, 
        ALL_TAB_COLUMNS.DATA_TYPE AS DATA_TYPE, 
        ALL_TAB_COLUMNS.DATA_PRECISION AS DATA_PRECISION, 
        ALL_TAB_COLUMNS.CHAR_LENGTH AS CHAR_LENGTH 
        FROM ALL_TAB_COLUMNS 
        WHERE ALL_TAB_COLUMNS.TABLE_NAME = tableName 
        ORDER BY ALL_TAB_COLUMNS.COLUMN_ID;

    CURSOR COM IS
        SELECT TC.COLUMN_NAME, CC.COMMENTS
        FROM   ALL_COL_COMMENTS CC
        JOIN   ALL_TAB_COLUMNS  TC 
            ON  CC.COLUMN_NAME = TC.COLUMN_NAME
            AND CC.TABLE_NAME  = TC.TABLE_NAME
        WHERE  CC.TABLE_NAME = tableName;

    CURSOR IND IS
    SELECT INDEX_NAME, COLUMN_NAME FROM ALL_IND_COLUMNS
    WHERE TABLE_NAME = tableName;
        
BEGIN
    DBMS_OUTPUT.PUT_LINE('Таблица: ' || tableName);
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE(RPAD(colNo, noLen) || ' ' || RPAD(colName, colLen) || ' ' || RPAD(colAttr, attrLen));
    DBMS_OUTPUT.PUT_LINE(RPAD('-', noLen, '-') || ' ' || RPAD('-', colLen, '-') || ' ' || RPAD('-', attrLen, '-'));

    FOR ROW IN RES LOOP
        colNo := TO_CHAR(ROW.COLUMN_ID);
        colName := ROW.COLUMN_NAME;
        dataType := RPAD('Type: ', attrNameLen) || ROW.DATA_TYPE;

        IF ROW.DATA_PRECISION IS NOT NULL THEN
            dataType := dataType || '(' || ROW.DATA_PRECISION || ')';
        END IF;
        
        IF ROW.CHAR_LENGTH > 0 THEN
            dataType := dataType || '(' || ROW.CHAR_LENGTH || ')';
        END IF;

        DBMS_OUTPUT.PUT_LINE(RPAD(colNo, noLen, ' ') || ' ' || RPAD(colName, colLen, ' ') || ' ' || RPAD(dataType, attrLen, ' '));

        
        FOR ROW_COM IN COM LOOP
            IF ROW_COM.COLUMN_NAME = ROW.COLUMN_NAME THEN
                DBMS_OUTPUT.PUT_LINE(RPAD(' ', noLen + colLen + 2) || RPAD('Commen: ',comLen) ||'"' || ROW_COM.COMMENTS || '"');
                EXIT;
            END IF;
        END LOOP;

        
        FOR ROW_IND IN IND LOOP
            IF ROW_IND.COLUMN_NAME = ROW.COLUMN_NAME THEN
                DBMS_OUTPUT.PUT_LINE(RPAD(' ', noLen + colLen + 2) || RPAD('Index: ',comLen) ||'"' || ROW_IND.INDEX_NAME || '"');
                EXIT;
            END IF;
        END LOOP;
    END LOOP;
END;
/
