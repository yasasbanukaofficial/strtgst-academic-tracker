package edu.ijse.strtgst.model;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import edu.ijse.strtgst.util.AlertUtil;

import java.time.LocalDate;

public class ChatBotModel {
    private static final String GOOGLE_API_KEY = "AIzaSyAboDpPm77ZEmlnGyyRK-Ta518yv6e9p9Q";

    public static String getResponse(String userInput) {
        System.setProperty("GOOGLE_API_KEY", GOOGLE_API_KEY);

        try {
            Client client = new Client.Builder().apiKey(GOOGLE_API_KEY).build();
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.0-flash",
                    """
                            You are an AI that only returns plain SQL INSERT statements — no code blocks, no labels, no explanations, no markdown formatting.
                            
                            Today's Date is """ + LocalDate.now() + """

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
                            - Remember the queries you add will be retrieved by the project's model to apply it in CalendarFx's views
                            - Remember when generating id's always generate random id's everytime user input something
                            - Only generate SQL INSERT INTO statements.
                            - Do not wrap the output in triple backticks (```), do not prefix with “sql” or any labels.
                            - Do not return any comments or explanations.
                            - Do not return anything if the user input is casual or unrelated (e.g., “Hello”, “How are you”).
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
                            
                            Now generate a valid SQL INSERT statement based only on the following user input. Do not wrap it, label it, or explain it:
                            
                            """ + userInput,
                    null
            );
            return response.text();
        } catch (Exception e) {
            AlertUtil.setErrorAlert("Error when generating the query through AI");
            return "Error:  " + e.getMessage();
        }
    }
}
