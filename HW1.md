#### 1. Реализовать сервлет с отображением в таблице списка еды (в памяти и БЕЗ учета пользователя)

- 1.1 По аналогии с `UserServlet` добавить `MealServlet` и `meals.jsp`
  - Задеплоить приложение (war) в Tomcat c `applicationContext=topjava` (приложение должно быть доступно по <a href="http://localhost:8080/topjava">http://localhost:8080/topjava</a>)
  - Попробовать разные деплои в Tomcat, remote и local debug
  - проверь url и applicationContext
  - Хранить нужно Meal и конвертировать ее в MealWithExceed когда отдаем список на отображение в JSP
  - Стили color можно применять ко всей строке таблицы tr, а не каждой ячейке.
- 1.2 Сделать отображения списка еды в JSP, цвет записи в таблице зависит от параметра `exceed` (красный/зеленый).
  - 1.2.1 Список еды захардкодить (те проинициализировать в коде, желательно чтобы в проекте инициализация была только в одном месте)
  - 1.2.2 Время выводить без 'T'
  - 1.2.3 Список выводим БЕЗ фильтрации по `startTime/endTime`
  - 1.2.4 Вариант реализации:
    - из сервлета преобразуете еду в `List<MealWithExceeded>`;
    - кладете список в запрос (`request.setAttribute`);
    - делаете `forward` на jsp для отрисовки таблицы (при `redirect` атрибуты теряются).
    - в `JSP` для цикла можно использовать `JSTL tag forEach`. Для подключения `JSTL` в `pom.xml` и шапку JSP нужно добавить:
```
    <dependency>
       <groupId>javax.servlet</groupId>
       <artifactId>jstl</artifactId>
       <version>1.2</version>
    </dependency>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    ...
```
### что использовать:
DateTimeFormatter можно сделать один заранее (он потокобезопасный в отличие от SimpleDateFormatter), а не создавать новый при каждом запросе.
for each в jsp 

### Optional
#### 2. Реализуем в ПАМЯТИ CRUD (create/read/update/delete) для еды
**Пример: <a href="https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/">Simple CRUD using Servlet/JSP</a>**
- Реализация таблицы в памяти у нас одна на всех. 
- при передаче параметров методы  getAttribute() and getParameter().
- 2.1 Хранение в памяти будет одна из наших CRUD реализаций (позже будет JDBC, JPA и DATA-JPA).
- учитывать в названии интерфейса, что он общий для реализаций, а реализация конкретная.
- 2.2 Работать с реализацией CRUD через интерфейс, который не должен ничего знать о деталях реализации (Map, DB или что-то еще).
- 2.3 Добавить поле `id` в `Meal/ MealWithExceed` и реализовать генерацию счетчика, УЧЕСТЬ МНОГОПОТОЧНОСТЬ СЕРВЛЕТОВ
    - volatile при ++ не помогает от многопоточности. Почему?
    - реализация вашей коллекции для хранения еды была также многопоточной
    - будет 2 CRUD (для еды и пользователей). А в реальном проекте их намного больше.
    - [обзор java.util.concurrent](https://habrahabr.ru/company/luxoft/blog/157273/)
- 2.4 Сделать форму редактирования в JSP: AJAX/JavaScript использовать НЕ надо, делаем через `<form method="post">` и `doPost()` в сервлете.
------protected void doPost(HttpServletRequest request, ...) {request.setCharacterEncoding("UTF-8");... если проблемы с кодировкой в POST.
- 2.5 Для ввода дат и времени можно использовать <a href="https://webref.ru/html/input/type">html5 типы</a>, хотя они поддерживаются не всеми браузерами (<a href="https://robertnyman.com/html5/forms/input-types.html">протестировать свой браузер</a>). В конце курса мы добавим <a href="http://xdsoft.net/jqplugins/datetimepicker/">DateTimePicker jQuery plugin</a>, который будет работать на всех браузерах.
#### что использовать?
-Не делайте дублирование кода MealsUtil. Возможно вам пригодятся константы LocalTime.MIN и LocalTime.MAX
