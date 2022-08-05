-- 코드를 입력하세요
SELECT OUTS.ANIMAL_ID, OUTS.NAME
FROM ANIMAL_INS INS
JOIN ANIMAL_OUTS OUTS
ON INS.ANIMAL_ID = OUTS.ANIMAL_ID
WHERE OUTS.DATETIME <= INS.DATETIME
ORDER BY INS.DATETIME;
