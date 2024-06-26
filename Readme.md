
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/img.png" alt="Logo" width="100" height="100">
  </a>

<h3 align="center">Тестовое задание</h3>
</div>

### Стек:
* Java 17
* Gradle
* JMH
### Для запуска:
1) git clone 
2) Перейдите в корень проекта
* Выполните следующие команды в терминале:
3) .\gradlew clean build
4) java -Xmx1G -jar build/libs/tz1-1.0-SNAPSHOT.jar (ваш файл txt/csv)
### Результаты:
1) lng.txt - 3 секунды, количечтво групп - 1910
![Скриншот 1](images/img_1.png)
2) lng-big.csv - 9-10 секунд, количечтво групп - 105036
![Скриншот 2](images/img_2.png)
##### Также если развернуть в среде разработки можно выполнить бенчмарк с помощью JMH




