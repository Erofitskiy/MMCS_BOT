package JSONClasses;

public class curricula {
    public int id;
    public int lessonid;
    public int subnum;
    public int subjectid;          // id предмета
    public String subjectname;     // Полное название предмета
    public String subjectabbr;     // Краткое название предмета
    public int teacherid;          // id преподавателя
    public String teachername;     // ФИО преподавателя
    public String teacherdegree;   // Степень преподавателя
    public int roomid;             // id аудитории
    public String roomname;        // Аудитория

    @Override
    public String toString() {
        return "[ subjectname: " + subjectname + ", teachername: " + teachername +
                ", roomname: " + String.valueOf(roomname) + " ]";
    }

}
/*
"id":8219,"lessonid":6385,"subnum":1,"subjectid":372,
 "subjectname":"Уравнения математической физики",
 "subjectabbr":"УМФ","teacherid":111,
 "teachername":"Моргулис Андрей Борисович",
 "teacherdegree":"Доцент","roomid":48,"roomname":"325"}
 */
