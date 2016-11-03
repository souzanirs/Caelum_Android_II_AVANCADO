package br.com.caelum.fj59.carangos.gcm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;


/**
 * Created by android6275 on 03/11/16.
 */

public class InformacoesDoUsuario {

    public static String getEmail(Context context){

        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if(account == null){
            return null;
        }

        return account.name;
    }

    private static Account getAccount(AccountManager accountManager) {

        Account[] accounts = (Account[]) accountManager.getAccountsByType("com.google");
        Account account = null;

        if(accounts.length > 0){
            account = accounts[0];
        }

        return account;
    }

}
