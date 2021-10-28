package booking.Entities;

import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 6835234265344603059L;
  public final String firstName;
  public final String secondName;
  public final String nickname;

  @Override
  public String toString() {
    return new String(String.format("User{\n fistName: %s,\n secondName: %s,\n nickname: %s\n}",
            this.firstName,
            this.secondName,
            this.nickname
    ));
  }

  public String prettyFormat() {
    return new String(String.format("Пассажир: %s %s\nимя пользователя: %s\n",
            this.firstName,
            this.secondName,
            this.nickname
    ));
  }

  public User(String name, String surname) {
    this.firstName = name;
    this.secondName = surname;
    this.nickname = makeNickname(name, surname);
  }

  public static String makeNickname(String name, String surname) {
    String nick = new String();
    char c = name.toLowerCase().charAt(0);
    nick+= c;
    nick+= surname.toLowerCase();
    return nick;
  }
}
