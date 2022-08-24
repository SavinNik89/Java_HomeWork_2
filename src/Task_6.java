
import java.util.Arrays;

public class Task_6 {
    public static void main(String[] args) {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 10, -1, -2, -3};
        System.out.println("Input array: " + Arrays.toString(array));
        System.out.println("A sorted array : " + Arrays.toString(mergeSort(array)));
    }

    public static int[] mergeSort (int[] array) {
        int middlePoint, endPoint; //переменные для средней и правой позиции в группах
        int indicatorStart, indicatorMiddle, indicatorTemp; //переменные для стартовой и средней позиции в группах и для рабочего массива
        int lastIndex = array.length-1; //последний индекс во входящем массиве
        int [] temp = new int[array.length]; //рабочий массив, равный по длине входящему

        for (int size = 1; size < lastIndex+1; size*=2) {
            //задаем цикл который будет увеличивать размер групп элементов, которые будут "сливаться"
            for (int startPoint=0; startPoint+size<=lastIndex; startPoint = startPoint+size*2) {
                //цикл который "формирует" группы элементов, сравнивает и записывает их в верном порядке
                middlePoint = startPoint+size;
                endPoint = middlePoint+size;
                if (endPoint > lastIndex) endPoint = lastIndex+1;//чтобы не выйти за пределы массива
                indicatorStart = startPoint;
                indicatorTemp = startPoint;
                indicatorMiddle = middlePoint;
                while (indicatorStart<middlePoint && indicatorMiddle<endPoint){
                    //пока мы идем по двум группам от левого к среднему и от среднего к последнему, сравниваем и записываем в темп
                    if (array[indicatorStart] < array[indicatorMiddle]) {
                        temp[indicatorTemp] = array[indicatorStart];
                        indicatorStart++;
                    }
                    else {temp[indicatorTemp] = array[indicatorMiddle];
                    indicatorMiddle++;}
                    indicatorTemp++;
                }
                while (indicatorStart<middlePoint){
                    //записываем оставшиеся элементы от левого до среднего
                    temp[indicatorTemp] = array[indicatorStart];
                    indicatorStart++;
                    indicatorTemp++;
                }
                while (indicatorMiddle<endPoint){
                    //записываем оставшиеся элементы от среднего до правового
                    temp[indicatorTemp] = array[indicatorMiddle];
                    indicatorMiddle++;
                    indicatorTemp++;
                }
                for (indicatorTemp = startPoint; indicatorTemp<endPoint; indicatorTemp++){
                    // переписывем отсортированную группу в исходный массив
                    array[indicatorTemp] = temp[indicatorTemp];
                }
            }
        }
        return array;
    }
}


