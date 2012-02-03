package com.ebaytools.util;

import com.ebaytools.kernel.entity.FileSearching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FileSearchingDataImpl implements TableModelCheckBox.IData {
    private List<FileSearching> fileSearchings;
    private static final List<String> nameColumn = Arrays.asList("ID", "Path", "Condition", "ListType", "LeftTime", "Interval", "RunTime");

    public FileSearchingDataImpl(List<FileSearching> fileSearchings) {
        this.fileSearchings = fileSearchings;
    }

    @Override
    public List<Object[]> getData() {
        List<Object[]> objectFields = new ArrayList<Object[]>();
        for (FileSearching fileSearching : fileSearchings) {
            Object[] objects = new Object[8];
            objects[0] = Boolean.FALSE;
            objects[1] = fileSearching.getId();
            objects[2] = fileSearching.getPath();
            objects[3] = fileSearching.getCondition();
            objects[4] = fileSearching.getListType();
            objects[5] = fileSearching.getDayLeft();
            objects[6] = fileSearching.getTimeInterval();
            Calendar cal = fileSearching.getRunTime();
            objects[7] = cal != null ?  FormatterText.dateformatter.format(cal.getTime()) : " - ";
            objectFields.add(objects);
        }
        return objectFields;
    }

    @Override
    public List<String> getNameColumn() {
        return nameColumn;
    }
}
