import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;


public class NeedForSpeed {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numberOfCars = Integer.parseInt(input.nextLine());
        LinkedHashMap<String, List<Integer>> cars = new LinkedHashMap<>();
        List<Integer> added = new ArrayList<>();
        for (int i = 0; i < numberOfCars; i++) {
            String[] addCars = input.nextLine().split("\\|");
            String carName = addCars[0];
            int mileage = Integer.parseInt(addCars[1]);
            int fuel = Integer.parseInt(addCars[2]);
            added = new ArrayList<>();
            added.add(0, mileage);
            added.add(1, fuel);
            cars.put(carName, added);
        }
        while (true) {
            String[] cmd = input.nextLine().split(" : ");
            if (cmd[0].equals("Stop")) {
                break;
            }

            switch (cmd[0]) {
                case "Drive":
                    int distance = Integer.parseInt(cmd[2]);
                    int neededFuel = Integer.parseInt(cmd[3]);
                    int availableFuel = cars.get(cmd[1]).get(1);
                    if (availableFuel < neededFuel) {
                        System.out.println("Not enough fuel to make that ride");
                    } else {
                        cars.get(cmd[1]).set(0, cars.get(cmd[1]).get(0) + distance);
                        cars.get(cmd[1]).set(1, availableFuel - neededFuel);
                        System.out.printf("%s driven for %d kilometers. %d liters of fuel consumed.\n", cmd[1], distance, neededFuel);
                        if (cars.get(cmd[1]).get(0) >= 100000) {
                            System.out.printf("Time to sell the %s!\n", cmd[1]);
                            cars.remove(cmd[1]);
                        }
                    }
                    break;
                case "Refuel":
                    int refuel = Integer.parseInt(cmd[2]);
                    int current = cars.get(cmd[1]).get(1);
                    if (current + refuel > 75) {
                        cars.get(cmd[1]).set(1, 75);
                        System.out.printf("%s refueled with %d liters%n", cmd[1], Math.abs(current-75));
                    } else {
                        cars.get(cmd[1]).set(1, cars.get(cmd[1]).get(1) + refuel);
                        System.out.printf("%s refueled with %d liters%n", cmd[1], refuel);
                    }
                    break;
                case "Revert":
                    int kilometers = Integer.parseInt(cmd[2]);
                    cars.get(cmd[1]).set(0, cars.get(cmd[1]).get(0) - kilometers);
                    if (cars.get(cmd[1]).get(0) <= 10000) {
                        cars.get(cmd[1]).set(0, 10000);
                    } else {
                        System.out.printf("%s mileage decreased by %d kilometers\n", cmd[1], kilometers);
                    }
                    break;
            }
        }
        cars.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    if (getMileages(e1.getValue()) == getMileages(e2.getValue())) {
                        return e1.getKey().compareTo(e2.getKey());
                    }
                    return Integer.compare(getMileages(e2.getValue()), getMileages(e1.getValue()));
                })
                .forEach((e) -> System.out.printf("%s -> Mileage: %d kms, Fuel in the tank: %d lt.\n", e.getKey(), getMileages(e.getValue()), getFuel(e.getValue())));

    }

    public static int getMileages(List<Integer> list) {
        return list.get(0);
    }

    public static int getFuel(List<Integer> list) {
        return list.get(1);
    }
}
