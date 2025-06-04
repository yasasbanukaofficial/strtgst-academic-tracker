package edu.ijse.strtgst.util;

import edu.ijse.strtgst.context.AppContext;
import edu.ijse.strtgst.model.AssignmentModel;
import edu.ijse.strtgst.model.StudentModel;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PromptBuilder {
    private static AppContext appContext = AppContext.getInstance();

    public static String buildSqlInsertPrompt(String userInput){
        return """
                            You are an AI that only returns plain SQL INSERT statements — no code blocks, no labels, no explanations, no markdown formatting.
                            
                            Today's Date and time is """ + LocalDateTime.now() + """

                            There are 3 tables in the MySQL database:
                            
                            -- Table Lecture  
                            CREATE TABLE lecture (  
                                lec_id VARCHAR(50),  
                                title VARCHAR(250),  
                                location VARCHAR(100) DEFAULT 'SCHOOL',  
                                full_day BOOLEAN DEFAULT FALSE,  
                                from_date DATETIME,  
                                to_date DATETIME,  
                                repeat_type VARCHAR(50) DEFAULT 'None',  
                                PRIMARY KEY (lec_id)  
                            );
                            
                            -- Table Exam  
                            CREATE TABLE exam (  
                                exam_id VARCHAR(50),  
                                title VARCHAR(250),  
                                location VARCHAR(100) DEFAULT 'SCHOOL',  
                                full_day BOOLEAN DEFAULT FALSE,  
                                from_date DATETIME,  
                                to_date DATETIME,  
                                repeat_type VARCHAR(50) DEFAULT 'None',  
                                PRIMARY KEY (exam_id)  
                            );
                            
                            -- Table Event  
                            CREATE TABLE event (  
                                event_id VARCHAR(50),  
                                title VARCHAR(250),  
                                location VARCHAR(100) DEFAULT 'SCHOOL',  
                                full_day BOOLEAN DEFAULT FALSE,  
                                from_date DATETIME,  
                                to_date DATETIME,  
                                repeat_type VARCHAR(50) DEFAULT 'None',  
                                PRIMARY KEY (event_id)  
                            );
                            
                            Instructions:
                            - Remember Ignore unclear or incomplete sentences (e.g., “i have a exam” — no action required).
                            - Remember Only return results for clear, actionable sentences.
                            - Remember Skip anything confusing, non-standard, or gibberish.
                            - Remember the queries you add will be retrieved by the project's model to apply it in CalendarFx's views
                            - Remember when generating id's always generate random id's everytime user input something
                            - Only generate SQL INSERT INTO statements.
                            - Do not wrap the output in triple backticks (```), do not prefix with “sql” or any labels.
                            - Do not return any comments or explanations.
                            - Do not return anything if the user input is casual or unrelated (e.g., “Hello”, “How are you”) or anything unrelated about events, exams, or lectures or if user ask about something that needs help with.
                            - Convert expressions like “Monday at 9pm” to full `YYYY-MM-DD HH:MM:SS` datetime.
                            - Do NOT use SQL functions like CONCAT, just give the final datetime.
                            - Set `full_day = TRUE` if the user says "full day" or similar.
                            - Default location is `'SCHOOL'` for exams and lectures, `'Cafe'` for events if not mentioned.
                            - Always generate a random ID for each row (e.g., `'EXM123456'`, `'EVT987654'`, `'LEC456789'`).
                            - Do not generate any statement if any of the details are missing because the database going to add the default values. Don't even add null to it.
                            - Do not pass null. If nay required value is missing just dont add that single data to the db since the db will add their default values.
                            - If user said something it will repeat or occur this way add it to the repeat_type column this way only for 
                              daily -> RRULE:FREQ=DAILY
                              weekly -> RRULE:FREQ=WEEKLY
                              monthly -> RRULE:FREQ=MONTHLY
                              yearly -> RRULE:FREQ=YEARLY
                              if there's nothing user mentioned about it put null only nothing else. 
                            - If the user didnt mention about what the exam, lecture or the event is just put a the table name as the title.
                            - Always generate a new, unique random ID for each new row, even if details appear similar.
                            - Always generate a wonderful random study name for the title if the user didn't give anything for the title.
                            - Remember If the user didnt mention about the ending time always put the ending time as 1 hour past the starting time likewise if the user didnt mention about the starting time always put the starting time as the current time.
                            
                            Now generate a valid SQL INSERT statement based only on the following user input. Do not wrap it, label it, or explain it:
                            
                            """ + userInput;
    }

    public static String askAboutStudies(String userInput, StringBuilder previousMsg) {
        return """
                 You are a helpful assistant your name is Strtgst Ai Helper Bot.
                 When asked for the name don't mention you are made by google just say you are Strtgst Ai Helper Bot which is powered by gemini.
                 Answer the following question briefly, with as much useful detail as possible in fewer than 150 characters.
                 Do not repeat the question or add extra text.
                 - Remember if the user answers like for jokes like blah, blah or something related to it or if the user didnt ask anything related to learning something, or even put few letters as an input just say didn't get the message as an answer, try something educative.
                 If the user ask about something related to previous chat/message, if its null or nothing there just ignore it here's the previous message passed as a Stringbuilder in java= """ + previousMsg.toString() + """
                 User's Question: """ + userInput;
    }

    public static String reminderGenerator() {
        return """
                You are a reminder generator AI for an educational app aimed at students and teenagers.
                Your task is to generate a random educational reminder that is inspiring, relevant, and informative.
                The reminder must be between 50 and 70 characters long.
                Only return the reminder — no explanations, no formatting, no extra text.""";
    }

    public static String buildSqlInsertAcademicsPrompt(String userInput, StringBuilder chatHistory){
        String gradeId = AssignmentModel.loadNextGradeID();
        String subjectId = AssignmentModel.loadNextSubjectID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String currentLoggedInUser = "";
        try {
            currentLoggedInUser = StudentModel.getStudentIdByUsername(appContext.getUsername());
            chatHistory.append(userInput);
            return """
                You are an AI that only returns plain SQL INSERT statements — no code blocks, no labels, no explanations, no markdown formatting.
                
                Today's Date and Time is: """ + formattedDate + """
                
                There are 2 tables in the MySQL database:
                
                Table: grade
                
                CREATE TABLE grade (
                    grade_id VARCHAR(5),
                    marks INT,
                    grade VARCHAR(2),
                    received_date DATE,
                    PRIMARY KEY (grade_id)
                );
                
                Table: subject
                
                CREATE TABLE subject (
                    sub_id VARCHAR(5) NOT NULL,
                    stud_id VARCHAR(4),
                    sub_name VARCHAR(50) NOT NULL,
                    total_marks INT,
                    PRIMARY KEY (sub_id),
                    FOREIGN KEY (stud_id) REFERENCES student(stud_id)
                        ON UPDATE CASCADE
                        ON DELETE CASCADE
                );
                
                Instructions:
                    You only return valid SQL INSERT INTO statements.
                    Never include code blocks, markdown, labels, or explanations — only the raw SQL query.
                    Only respond to clear, actionable user inputs (e.g., "Add subject Math for student").
                    Ignore casual, vague, or incomplete sentences (e.g., "I have a grade", "hello").
                    Do not insert null values — skip columns that were not mentioned.
                    Do not generate anything if required values are missing.
                    Use the current date for received_date if no specific date is given.
                    The subject title (sub_name) must be 100% present — if it’s missing or unclear, skip insertion.
                    Convert expressions like “today”, “yesterday”, or “Monday” into full YYYY-MM-DD format.
                    Never include irrelevant things like event, exam, or lecture — this is only about subjects and grades now.
                    An example for a user input is somewhat like this so get an idea, Add a Maths subject to the database
                    Remember to show the insert query too.
                    Remember after inserting into the subject table, also insert a corresponding row into the grade table using the same subject ID and total marks, for the received_date use the current date & time and use this as the id for the grade = """ + gradeId + """
                    Remember to use the semicolon at the end of each query ';'
                    Remember if there are no inputs of a total_marks for subject you dont need to add a grade record just send one query.
                    Remember sub_name is not case sensitive Maths is also equal to maths not different words
                    Be more careful user might enter simple inputs like 'Maths with 10' , 'Maths 10' it means the sub_name is Maths with total_marks of 10 and in the grade table add the corresponding sub_id related to that sub_name in this case sub_id for the Maths properly.
                    
                    Finally Remember these are what you need to add
                    For subject you need to add the sub_id """ + subjectId + """
                    , stud_id = """ + currentLoggedInUser + """
                    sub_name (provided by the user input), total_marks (provided by the user input sometimes the user won't input it so make it 0 as the default value)
                    And remember to add Grade if user enter the marks (Sometimes the user might input it shortly like Add Maths with 10, 10 is the marks for the subject and you should add it to the subject total_marks and grade marks columns respectively) for the subject.
                    For grade you need to add the grade_id = """ + gradeId + """ 
                    , sub_id = """ + subjectId + """
                    , marks (provided by the user input), grade (input it like this 75 or more → Grade = "A", 65 to 74 → Grade = "B", 55 to 64 → Grade = "C", 45 to 54 → Grade = "D", Below 45 → Grade = "F"), received_date (put today's date and time provided above)
                    You should follow this.
                    
                    Any of this shouldn't be null specially the sub_id, stud_id, grade_id
                    
                    Finally if the user enter the same subject name in another way like for Maths as maths just update the name of the subject put a update query with relevant data. To do this you need access to the past conversations know so here's the past conversations user had the entire time = """ + chatHistory.toString() + """ 
                    
                Now generate a valid SQL INSERT statement based only on the following user input. Do not wrap it, label it, or explain it.
                Here's the user's input
                user input = 
                """ + userInput;
        } catch (SQLException e) {
            AlertUtil.setErrorAlert("Error when fetching student id");
        }
        return null;
    }
}
