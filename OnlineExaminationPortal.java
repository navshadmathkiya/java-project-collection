


import java.util.*;

class Question {
    String questionsText;
    String[] options;
    int correctAnswer;

    Question(String questionsText, String[] options, int correctAnswer) {
        this.questionsText = questionsText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    void displayQuestions() {
        System.out.println(questionsText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}

class ExamSystem {
    ArrayList<Question> questions = new ArrayList<>();
    int score = 0;
    boolean timeOver = false;

    void addQuestions() {
        questions.add(new Question("What is the capital of India?",
                new String[]{"Delhi", "Mumbai", "Ahmedabad", "Chennai"}, 1));

        questions.add(new Question("Who is known as the Father of the Indian Constitution?",
                new String[]{"Mahatma Gandhi", "Dr. B. R. Ambedkar", "Jawaharlal Nehru", "Sardar Patel"}, 2));

        questions.add(new Question("Which river is known as the 'Ganga of the South'?",
                new String[]{"Godavari", "Krishna", "Kaveri", "Yamuna"}, 3));

        questions.add(new Question("In which year did India get independence?",
                new String[]{"1945", "1946", "1947", "1948"}, 3));

        questions.add(new Question("Which is the national animal of India?",
                new String[]{"Elephant", "Tiger", "Lion", "Leopard"}, 2));

        questions.add(new Question("Which city is known as the Silicon Valley of India?",
                new String[]{"Hyderabad", "Bangalore", "Pune", "Chennai"}, 2));

        questions.add(new Question("Who was the first Prime Minister of India?",
                new String[]{"Lal Bahadur Shastri", "Mahatma Gandhi", "Jawaharlal Nehru", "Indira Gandhi"}, 3));

        questions.add(new Question("Which Indian state has the longest coastline?",
                new String[]{"Maharashtra", "Tamil Nadu", "Gujarat", "Kerala"}, 3));

        questions.add(new Question("Which festival is known as the 'Festival of Lights'?",
                new String[]{"Holi", "Diwali", "Navratri", "Eid"}, 2));

        questions.add(new Question("Who is the President of India as of 2025?",
                new String[]{"Droupadi Murmu", "Ram Nath Kovind", "Narendra Modi", "Amit Shah"}, 1));
    }

    void StartExam() {
        Collections.shuffle(questions);
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 7 && !timeOver; i++) {
            Question q = questions.get(i);
            q.displayQuestions();

            System.out.print("Enter your answer (1–4): ");
            int ans = sc.nextInt();

            if (ans == q.correctAnswer)
                score++;
            System.out.println();
        }

        if (!timeOver) {
            System.out.println("Exam completed successfully!");
            System.out.println("Your Score: " + score + "/7");
        } else {
            System.out.println("\nTime Over! Late submissions not accepted.");
        }
    }
}

class ExamTimer extends Thread {
    ExamSystem exam;

    ExamTimer(ExamSystem exam) {
        this.exam = exam;
    }

    public void run() {
        for (int i = 60; i >= 0; i--) {  // 60 seconds timer
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }

            if (exam.timeOver)
                return;
        }
        exam.timeOver = true;
        System.out.println("\nTime is up! Auto submission done.\n");
    }
}

public class OnlineExaminationPortal {
    public static void main(String args[]) {
        ExamSystem exam = new ExamSystem();
        exam.addQuestions();

        ExamTimer timer = new ExamTimer(exam);
        timer.start();

        exam.StartExam();
    }
}
