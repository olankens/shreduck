package com.example.shreduck.bll.services;

import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;

import java.util.List;

public interface PresetService {

    Preset create(Preset preset, Member current);

    void delete(Long id, Member current);

    Preset detail(Long id, Member current);

    List<Preset> export(Member current);

}
