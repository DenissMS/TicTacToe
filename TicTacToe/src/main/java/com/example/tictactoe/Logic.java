package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Kerat on 26.01.14.
 */
public class Logic {
    byte i, j, o, v, h, d1, d2, sv, sh, sd1, sd2, e, l, T, s = 0, self;
    int n,m;
    int player,comp;
    boolean wc=false,wp=false, f;
    static byte[] p = {11, 13, 31, 33, 0 };
    static byte[] F = { 12, 21, 23, 32 };
    byte[] log = new byte[9];
    byte[] t = new byte[6];
    Random rand = new Random();
    byte[][] a= new byte[4][4];
    Button[] buttons;
    TextView notify;
    Button play;

    public Logic(Button[] buttons, TextView notify, Button play){
        this.buttons = buttons;
        this.notify = notify;
        this.play = play;
    }

    public void FirstStep()
    {
        T = 4;
        for (i = 0; i < 5; i++)
            t[i] = p[i];
        f = rand.nextBoolean();
        if (f) {
            player = R.drawable.tic_color;
            comp = R.drawable.tac_color; } // Если выпало 1, то первым ходит игрок
        else {
            player =  R.drawable.tac_color;
            comp = R.drawable.tic_color;
            InitAI();
        } // Если выпало 0, то первым ходит компьютер
    }

    void Slogic()
    {
        n = 0;
        if (f)
        {
            if (a[2][2] == 0)
            for (i = 0; i < 4; i++)
                if (p[i] == l) { n = 22; return; }
            //if ((Math.Abs(i - l / 10) + Math.Abs(j - l % 10)) > 1)
            //DecrCombs(i * 10 + j);
            if (a[2][2] == 1)
            if ((a[1][3] == 2 && a[3][1] == 2) || (a[1][1] == 2 && a[3][3] == 2))
            { n = F[rand.nextInt(F.length)]; return; }
            int c = 0;
            for (i = 0; i < 4; i++)
                if (a[F[i] / 10][F[i] % 10] == 2)
            {
                c++;
                if (c == 2)
                    if (CheckEmpty(22)) return;
            }
        }
        else
        {
            if (s == 2 || s == 2)
            {
                for (i = 0; i < T; i++)
                {
                    int tmp1=BuildLine(log[1],t[i]);
                    int tmp2=BuildLine(log[2], t[i]);
                    int tmp3 = BuildLine(log[1], log[2]);
                    if (a[tmp3 / 10][tmp3 % 10] == 0) self++;
                    if (a[tmp1 / 10][tmp1 % 10] == 0) self++;
                    if (a[tmp2 / 10][tmp2 % 10] == 0) self++;
                    if (self > 1) n = t[i];
                }
            }
        }
        if (T > 0)
            if(T==1){
                n = t[0];
            }
            else {
                int r = rand.nextInt(T - 1);
                n = t[r];
            }
        return;
    }

    int BuildLine(byte o,byte p)
    {
        int S1 = o / 10 + p / 10;
        int S2 = o % 10 + p % 10;
        if (S1 == 4 || S2 == 4)
        { S1 /= 2; S2 /= 2; }
        if (S1 > 4) S1 -= 4;
        if (S1 < 0) S1 += 4;
        if (S2 > 4) S2 -= 4;
        if (S2 < 0) S2 += 4;
        return S1 * 10 + S2;
    }

    public void DecrCombs(int n)
    {
        for (int k = 0; k < T; k++)
        {
            if (t[k] == n)
            {
                for (int q = k; q < T; q++)
                    t[q] = t[q + 1];
                T--; break;
            }
        }
    }
    public void SetButtons(boolean en)
    {
        for(Button btn : buttons){
            btn.setEnabled(en);
        }
    }
    public void Step(Button btn, byte last)
    {
        try {
            l = last;
            if (a[l / 10][l % 10] == 0)
            { btn.setBackgroundResource(player);
                a[l / 10][l % 10] = 2;
                if (CheckEmpty())
                    InitAI();
                if (!CheckEmpty())
                    EndGame("Ничья"); }
            else notify.setText("Занято");
        }
        catch (Exception e){
        }
    }
    public void InitAI()
    {
        s++;
        DecrCombs(l);
        Slogic();
        Flogic();
        if (n == 0)
            for (i = 1; i < 4; i++)
                for (j = 1; j < 4; j++)
                    if (a[i][j] == 0) n = i * 10 + j;
        a[n / 10][n % 10] = 1; log[s] = (byte)n;
        DecrCombs(n);
        draw(n);
        if (wc&&!wp )
        { EndGame("Вы проиграли!"); }
        if (wp)
        { EndGame("Вы выиграли!"); }
    }

    public void draw(int n)
    {
        switch (n)
        {
            case 11: buttons[0].setBackgroundResource(comp); break;
            case 12: buttons[1].setBackgroundResource(comp); break;
            case 13: buttons[2].setBackgroundResource(comp); break;
            case 21: buttons[3].setBackgroundResource(comp); break;
            case 22: buttons[4].setBackgroundResource(comp); break;
            case 23: buttons[5].setBackgroundResource(comp); break;
            case 31: buttons[6].setBackgroundResource(comp); break;
            case 32: buttons[7].setBackgroundResource(comp); break;
            case 33: buttons[8].setBackgroundResource(comp); break;
        }
    }
    boolean CheckEmpty()
    {
        for (i = 1; i < 4; i++)
            for (j = 1; j < 4; j++)
                if (a[i][j] == 0) return true;
        return false;
    }
    boolean CheckEmpty(int v)
    {
        if (a[v / 10][v % 10] == 0) { n = v; return true; }
        return false;
    }

    void Flogic()
    {
        d1 = 0; d2 = 0;
        sd1 = 0; sd2 = 0;
        for(i = 1; i < 4; i++)
        {
            v = 0; h = 0; sv = 0; sh = 0;
            for (j = 1; j < 4; j++)
            {
                if (a[i][j] == 1) sh++; // Проверка горизонтальных линий
                if (sh == 2)
                    for (o = 1; o < 4; o++) if (a[i][o] == 0) { n = i * 10 + o; wc = true; }
                if (a[i][j] == 2) h++;
                if (h == 3) { wp=true; n = 0; return; }
                if (h == 2)
                    if (!wc)
                    { for (o = 1; o < 4; o++) if (a[i][o] == 0) { n = i * 10 + o; } }
                if (a[j][i] == 1) sv++; // Проверка вертикальных линий
                if (sv == 2)
                    for (o = 1; o < 4; o++) if (a[o][i] == 0) { n = o * 10 + i; wc = true; }
                if (a[j][i] == 2) v++;
                if (v == 3) { wp = true; n = 0; return; }
                if (v == 2)
                    if (!wc)
                    { for (o = 1; o < 4; o++) if (a[o][i] == 0) { n = o * 10 + i; } }
            }
            if (a[i][i] == 1) sd1++; ; // Проверка первой диагонали
            if (sd1 == 2)
                for (o = 1; o < 4; o++)
                    if (a[o][o] == 0) { n = o * 10 + o; wc = true; }
            if (a[i][i] == 2) d1++;
            if (d1 == 3) { wp = true; n = 0; return; }
            if (d1 == 2)
                if (!wc)
                { for (o = 1; o < 4; o++) if (a[o][o] == 0) { n = o * 10 + o; return; } }
            if (a[i][4-i] == 1) sd2++; // Проверка второй диагонали
            if (sd2 == 2)
                for (o = 1; o < 4; o++)
                    if (a[o][4 - o] == 0) { n = o * 10 + (4 - o); wc = true; }
            if (a[i][4-i] == 2) d2++;
            if (d2 == 3) { wp = true; n = 0; return; }
            if (d2 == 2)
                if (!wc)
                { for (o = 1; o < 4; o++) if (a[o][4 - o] == 0) { n = o * 10 + (4 - o); return; } }
        }
    }
    public void Clear()
    {
        for (i = 1; i < 4; i++)
        for (j = 1; j < 4; j++)
            a[i][j] = 0;
        wc = false; wp = false;
        for(Button btn : buttons){
            btn.setBackgroundResource(R.color.empty);
        }
        s = 0;
        notify.setText("");
    }
    public void EndGame(String res)
    {
        notify.setText(res);
        SetButtons(false);
        play.setText("Заново");
        play.setEnabled(true);
    }
}
