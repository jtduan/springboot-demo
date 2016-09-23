package rest.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.constants.Role;
import rest.controller.UserController;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

@Deprecated
public class RolesConverter implements AttributeConverter<EnumSet<Role>, String> {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public String convertToDatabaseColumn(EnumSet<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for(Role role:roles){
            sb.append(role.getName()).append(",");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }

    @Override
    public EnumSet<Role> convertToEntityAttribute(String roles) {
        EnumSet set = EnumSet.noneOf(Role.class);
        for(String role_str:roles.split(",")){
            Role role = Role.valueOf(role_str);
            if(role==null){
                logger.error("找不到ROLE:"+role_str);
                continue;
            }
            set.add(role);
        }
        return set;
    }
}
