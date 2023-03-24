import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {

        int count = 0;
        int bossId = 0;
        String line;
        String[] subString;
        List<Employee> allEmployees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {

            while ((line = reader.readLine()) != null) {

                if (Character.isDigit(line.charAt(0))) {

                    subString = line.split(";");
                    Employee employee = new Employee();

                    if (subString[1].equals("")) {
                        bossId = count;
                    } else {
                        employee.setBossId(Long.parseLong(subString[1]));
                    }
                    employee.setId(Long.parseLong(subString[0]));
                    employee.setName(subString[2]);
                    employee.setPosition(subString[3]);
                    allEmployees.add(employee);
                    count++;
                }
            }
        }

        for (int i = 0; i < allEmployees.size(); i++) {
            for (int j = 0; j < allEmployees.size(); j++) {
                if (allEmployees.get(i).getId() == allEmployees.get(j).getBossId()) {
                    allEmployees.get(j).setBossId(allEmployees.get(i).getId());
                    allEmployees.get(j).setBoss(allEmployees.get(i));
                    allEmployees.get(i).getSubordinate().add(allEmployees.get(j));
                }
            }
        }
        return allEmployees.get(bossId);
    }
}
