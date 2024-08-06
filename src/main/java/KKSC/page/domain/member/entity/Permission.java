package KKSC.page.domain.member.entity;

public enum Permission {

    //관리자
    permission_level0("ROLE_PERMISSION_LEVEL0", "관리자"),
    //일반회원
    permission_level1("ROLE_PERMISSION_LEVEL1", "정회원"),
    //준회원
    permission_level2("ROLE_PERMISSION_LEVEL2", "준회원");

    private final String roleName;
    private final String description;

    Permission(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }
}
