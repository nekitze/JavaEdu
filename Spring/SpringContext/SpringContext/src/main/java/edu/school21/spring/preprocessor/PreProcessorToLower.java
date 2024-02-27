package edu.school21.spring.preprocessor;

public class PreProcessorToLower implements PreProcessor{
    @Override
    public String process(String message) {
        return message.toLowerCase();
    }
}
