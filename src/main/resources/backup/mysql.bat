cd\
if exist bd_santarosa (
echo estou aki
pause
cd Arquivos de Programas\Mysql\Mysql Server 5.5\bin\
mysqldump -u root -proot -e -x bd_santarosa > C:\bd_santarosa\%date:/=-%.sql
pause
)
else (
cd\
md bd_santarosa
cd Arquivos de Programas\Mysql\Mysql Server 5.5\bin\
mysqldump -u root -proot -e -x bd_santarosa > C:\bd_santarosa\%date:/=-%.sql
)
