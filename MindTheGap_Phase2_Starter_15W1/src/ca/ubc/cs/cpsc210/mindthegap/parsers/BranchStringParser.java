package ca.ubc.cs.cpsc210.mindthegap.parsers;


import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for route strings in TfL line data
 */
public class BranchStringParser {

    /**
     * Parse a branch string obtained from TFL
     *
     * @param branch  branch string
     * @return       list of lat/lon points parsed from branch string
     */

    public static List<LatLon> parseBranch(String branch) {
        if(branch.isEmpty()) return new ArrayList<LatLon>();

        List<LatLon> latLons=new ArrayList<LatLon>();
        String b=spaceRemover(branch);
        b=b.replace("[[[","@");
        b=b.replace("]]]","@");
        b=b.replace("],[","@");

        int i=0;
        while(i<b.length()){
            if(b.charAt(i)=='@'){
                for(int k=i+1;k<b.length();k++){
                    if(b.charAt(k)=='@'){
                        latLons.add(createLatlon(b.substring(i+1,k)));
                        break;
                    }
                }
            }
            i++;
        }

        return latLons;   // stub
    }



    private static String spaceRemover(String s){
        if(s.isEmpty()) return "";
        if(s.charAt(0)==' ') return spaceRemover(s.substring(1));
        else return s.charAt(0)+spaceRemover(s.substring(1));
    }

    private static LatLon createLatlon(String s){
        int index=s.indexOf(',');
        return new LatLon(Double.parseDouble(s.substring(index+1,s.length())),
                Double.parseDouble(s.substring(0,index)));
        //Note LatLon class and lineString latlon order are different
    }
}
