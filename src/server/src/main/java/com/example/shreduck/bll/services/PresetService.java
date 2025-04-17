package com.example.shreduck.bll.services;

import com.example.shreduck.api.models.preset.forms.PresetForm;
import com.example.shreduck.dl.entities.Member;
import com.example.shreduck.dl.entities.Preset;

import java.util.List;

public interface PresetService {

    Preset create(Preset preset, Member current);

    void delete(Long id, Member current);

    Preset detail(Long id, Member current);

    Preset detailUnlockable(Long id);

    List<Preset> export(Member current);

    List<Preset> exportUnlockable();

    void unlock(Long id, Member current);

    Preset update(Long id, PresetForm form, Member current);

}
