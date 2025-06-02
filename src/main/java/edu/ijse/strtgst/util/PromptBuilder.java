package edu.ijse.strtgst.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PromptBuilder {
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
                            - Default location is `'SCHOOL'` if not mentioned.
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
}
